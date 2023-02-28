const state = {
    superiorList: [],
    goodsSuperiorList: []
}

const mutations = {
    ADD_SUPERIOR: (state,value) => {
        state.superiorList.push(value)
    },
    DEL_SUPERIOR: (state) => {
        state.superiorList.pop()
    },
    EMPTY_SUPERIOR: (state) => {
        state.superiorList = []
    },

    ADD_GOODS: (state,value) => {
        state.goodsSuperiorList.push(value)
    },
    DEL_GOODS: (state) => {
        state.goodsSuperiorList.pop()
    },
    EMPTY_GOODS: (state) => {
        state.goodsSuperiorList = []
    },
}

export default {
    state,
    mutations,
}