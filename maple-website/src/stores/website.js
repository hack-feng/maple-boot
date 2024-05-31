import { Session } from '../utils/storage';
import { getAllConfigList } from '../api/common';

/**
 * 获取配置数据
 */
export function getSiteConfig(_key) {
    return (() => {
        const configStore = Session.get(_key);
        if (configStore) {
            return configStore;
        } else {
            getAllConfigList().then(res => {
                for (let config of res) {
                    Session.set(config.configKey, config.configValue);
                }
            })
        }
        return Session.get(_key);
    })()
}