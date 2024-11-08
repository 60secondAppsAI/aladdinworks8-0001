import http from "../http-common"; 

class NetworkSwitchService {
  getAllNetworkSwitchs(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/networkSwitch/networkSwitchs`, searchDTO);
  }

  get(networkSwitchId) {
    return this.getRequest(`/networkSwitch/${networkSwitchId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/networkSwitch?field=${matchData}`, null);
  }

  addNetworkSwitch(data) {
    return http.post("/networkSwitch/addNetworkSwitch", data);
  }

  update(data) {
  	return http.post("/networkSwitch/updateNetworkSwitch", data);
  }
  
  uploadImage(data,networkSwitchId) {
  	return http.postForm("/networkSwitch/uploadImage/"+networkSwitchId, data);
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

export default new NetworkSwitchService();
