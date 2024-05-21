import request from '/@/utils/request';

/**
 * 用户api接口集合
 */
export function useGenerateApi() {
    return {
        // 分页查询代码生成业务列表
        getPageList(data: object) {
            return request({
                url: '/generate/getPageList',
                method: 'post',
                data: data
            })
        },
        // 查询据库列表
        getDbList(data: object) {
            return request({
                url: '/generate/getDbList',
                method: 'post',
                data: data
            })
        },
        // 将数据库表导入代码生成业务列表
        importTable(data: object) {
            return request({
                url: '/generate/importTable',
                method: 'post',
                data: data
            })
        },
        // 预览生成代码
        preview(id: number) {
            return request({
                url: '/generate/preview/' + id,
                method: 'get'
            })
        },
        // 获取详细信息
        getInfo(id: number) {
            return request({
                url: '/generate/' + id,
                method: 'get'
            })
        },
        updateGenTable(data: object) {
            return request({
                url: '/generate/updateGenTable',
                method: 'post',
                data: data
            }) 
        },
        deleteById(id: number) {
            return request({
                url: '/generate/' + id,
                method: 'delete'
            })
        },
        download(tableName: String) {
            return request({
                url: '/generate/download/' + tableName,
                method: 'get',
                responseType: "blob"
            })
        },
        batchDownload(data: object) {
            return request({
                url: '/generate/batchDownload',
                method: 'post',
                data: data,
                responseType: "blob"
            })
        }
    }
}
