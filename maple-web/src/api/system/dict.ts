import request from '/@/utils/request';

/**
 * 字典api接口集合
 */
export function useDictApi() {
    return {
        // 分页获取字典
        getPageList(data: object) {
            return request({
                url: '/dict/getPageList',
                method: 'post',
                data: data
            })
        },
        // 查询字典详细
        getDictById(id: number) {
            return request({
                url: '/dict/' + id,
                method: 'get'
            })
        },
        // 新增字典
        createDict(data: object) {
            return request({
                url: '/dict/createDictType',
                method: 'post',
                data: data
            })
        },
        // 修改字典
        updateDict(data: object) {
            return request({
                url: '/dict/updateDictType',
                method: 'post',
                data: data
            })
        },
        // 删除字典
        deleteDict(id: number) {
            return request({
                url: '/dict/' + id,
                method: 'delete'
            })
        },
        // 根据字典type获取字典数据
        getDictByCode(dictCode: string){
            return request({
                url: '/dict/getDictByCode/' + dictCode,
                method: 'get'
            })
        },
        // 获取字典类型列表
        getDictTypeList(){
            return request({
                url: '/dict/getDictTypeList',
                method: 'post'
            })
        }
    }
}
