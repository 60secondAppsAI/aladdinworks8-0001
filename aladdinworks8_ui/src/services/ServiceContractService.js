import http from "../http-common"; 

class ServiceContractService {
  getAllServiceContracts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/serviceContract/serviceContracts`, searchDTO);
  }

  get(serviceContractId) {
    return this.getRequest(`/serviceContract/${serviceContractId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/serviceContract?field=${matchData}`, null);
  }

  addServiceContract(data) {
    return http.post("/serviceContract/addServiceContract", data);
  }

  update(data) {
  	return http.post("/serviceContract/updateServiceContract", data);
  }
  
  uploadImage(data,serviceContractId) {
  	return http.postForm("/serviceContract/uploadImage/"+serviceContractId, data);
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

export default new ServiceContractService();
