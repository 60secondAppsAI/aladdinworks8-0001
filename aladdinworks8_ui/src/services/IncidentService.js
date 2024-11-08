import http from "../http-common"; 

class IncidentService {
  getAllIncidents(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/incident/incidents`, searchDTO);
  }

  get(incidentId) {
    return this.getRequest(`/incident/${incidentId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/incident?field=${matchData}`, null);
  }

  addIncident(data) {
    return http.post("/incident/addIncident", data);
  }

  update(data) {
  	return http.post("/incident/updateIncident", data);
  }
  
  uploadImage(data,incidentId) {
  	return http.postForm("/incident/uploadImage/"+incidentId, data);
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

export default new IncidentService();
