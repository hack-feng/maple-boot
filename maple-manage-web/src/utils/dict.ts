import { ref, toRefs } from 'vue';
import { useDictStore } from '/@/stores/dict'
import { useDictApi } from '/@/api/system/dict'

const useDict = useDictApi();

/**
 * 获取字典数据
 */
export function parseDict(...args) {
    const res = ref({});
    return (() => {
        for (let dictType of args) {
            res.value[dictType] = [];
            // dictType字典名称
            const dictStore = useDictStore().getDict(dictType);
            // 如果dicts有值，说明仓库中已经存储，直接使用
            if (dictStore) {
                res.value[dictType] = dictStore;
            } else {
                // 如果无值，代表仓库中还未存储，调用获取字典标签的接口，参数dictType代表要查询的字典名称
                useDict.getDictByCode(dictType).then(resp => {
                    res.value[dictType] = resp.map(p => ({
                        label: p.dictLabel,
                        value: p.dictValue,
                        elTagType: p.listClass,
                        elTagClass: p.cssClass
                    })).reduce((acc, item) => {
                        acc[item.value] = item;
                        return acc;
                    }, {})
                    useDictStore().setDict(dictType, res.value[dictType]);
                })
            }
        }
        return toRefs(res.value);
    })()
}