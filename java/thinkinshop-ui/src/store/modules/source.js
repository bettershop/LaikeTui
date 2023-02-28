import { getDictionaryInfo } from '@/api/members/membersList'
import { getStorage, setStorage, removeSource } from '@/utils/storage'

const getDefaultState = () => {
    return {
      source: getStorage('laike_source') ? getStorage('laike_source') : {},
    }
}

  const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
    },

    SET_SOURCE: (state, source) => {
        state.source = source
    },
}

const actions = {
    // 获取来源
    getSource({ commit }) {
        return new Promise((resolve, reject) => {
            getDictionaryInfo({
                api: 'saas.dic.getDictionaryInfo',
                key: '来源'
            })
            .then(response => {
                console.log(response);
                const list = response.data.data.list.map(item => {
                    return {
                        label: item.text,
                        value: item.value
                    }
                })
                setStorage('laike_source',list)
                console.log(list);
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
