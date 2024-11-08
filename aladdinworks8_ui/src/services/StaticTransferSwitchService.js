import http from "../http-common"; 

class StaticTransferSwitchService {
  getAllStaticTransferSwitchs(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/staticTransferSwitch/staticTransferSwitchs`, searchDTO);
  }

  get(staticTransferSwitchId) {
    return this.getRequest(`/staticTransferSwitch/${staticTransferSwitchId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/staticTransferSwitch?field=${matchData}`, null);
  }

  addStaticTransferSwitch(data) {
    return http.post("/staticTransferSwitch/addStaticTransferSwitch", data);
  }

  update(data) {
  	return http.post("/staticTransferSwitch/updateStaticTransferSwitch", data);
  }
  
  uploadImage(data,staticTransferSwitchId) {
  	return http.postForm("/staticTransferSwitch/uploadImage/"+staticTransferSwitchId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new StaticTransferSwitchService();
