import { defineStore } from 'pinia';
import { Session } from '/@/utils/storage';

export const useDictStore = defineStore('dicts',{
    state:() => ({
        dicts: new Array()
    }),
    actions:{
        // 获取字典
        getDict(_key) {
            if (_key == null || _key == "") {
                return null;
            }
            if (Session.get(_key)) {
                return Session.get(_key);
            } 
            try {
                for (let dict of this.dicts) {
                    if (dict.key == _key) {
                        return dict.value;
                    }
                }
            } catch (e) {
                console.log(e)
            }
            return null;
        },
        // 设置字典
        async setDict(_key, value) {
            if (_key !== null && _key !== "") {
                this.dicts.push({
                    key: _key,
                    value: value
                });
                Session.set(_key, value);
            }
        },
        // 删除字典
        async removeDict(_key) {
            var bln = false;
            try {
                for (let i = 0; i < this.dicts.length; i++) {
                    if (this.dicts[i].key == _key) {
                        this.dicts.splice(i, 1);
                        return true;
                    }
                }
            } catch (e) {
                bln = false;
            }
            return bln;
        },
        // 清空字典
        cleanDict() {
            this.dicts = new Array();
        },
    }
})