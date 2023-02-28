const state = {
    tag: 0
}

const mutations = {
    CHANGE_TAG: (state,value) => {
        state.tag = value
    }
}

const actions = {
    toggleTag({ commit }, tag) {
        commit('CHANGE_TAG', tag)
    }
}

export default {
    state,
    mutations,
    actions
}