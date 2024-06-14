import { Session } from '../utils/storage';
import { getAllConfigList } from '../api/common';

/**
 * 获取配置数据
 */
export function getSiteConfig(_key) {
    return (async () => {
        let configStore = Session.get(_key);
        if (!configStore) {
            await getAllConfigList().then(res => {
                for (let config of res) {
                    Session.set(config.configKey, config.configValue);
                    if(config.configKey === _key){
                        configStore = config.configValue;
                    }
                }
                return configStore;
            })
        }
        return configStore;
    })()
}