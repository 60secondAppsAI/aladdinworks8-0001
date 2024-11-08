import http from "../http-common"; 

class CapacitySensorService {
  getAllCapacitySensors(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/capacitySensor/capacitySensors`, searchDTO);
  }

  get(capacitySensorId) {
    return this.getRequest(`/capacitySensor/${capacitySensorId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/capacitySensor?field=${matchData}`, null);
  }

  addCapacitySensor(data) {
    return http.post("/capacitySensor/addCapacitySensor", data);
  }

  update(data) {
  	return http.post("/capacitySensor/updateCapacitySensor", data);
  }
  
  uploadImage(data,capacitySensorId) {
  	return http.postForm("/capacitySensor/uploadImage/"+capacitySensorId, data);
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

export default new CapacitySensorService();
