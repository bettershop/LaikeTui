import request from '@/api/https'

class Base {
  post(params) {
    return request({
      method: 'post',
      params: params
    });
  }
}

export default Base;
