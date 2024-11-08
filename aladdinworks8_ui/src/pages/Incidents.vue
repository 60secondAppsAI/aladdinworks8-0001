<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <incident-table
            v-if="incidents && incidents.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:incidents="incidents"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-incidents="getAllIncidents"
             >

            </incident-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import IncidentTable from "@/components/IncidentTable";
import IncidentService from "../services/IncidentService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    IncidentTable,
  },
  data() {
    return {
      incidents: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllIncidents(sortBy='incidentId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await IncidentService.getAllIncidents(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.incidents.length) {
					this.incidents = response.data.incidents;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching incidents:", error);
        }
        
      } catch (error) {
        console.error("Error fetching incident details:", error);
      }
    },
  },
  mounted() {
    this.getAllIncidents();
  },
  created() {
    this.$root.$on('searchQueryForIncidentsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllIncidents();
    })
  }
};
</script>
<style></style>
