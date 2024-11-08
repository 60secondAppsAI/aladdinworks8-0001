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
            <capacitySensor-table
            v-if="capacitySensors && capacitySensors.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:capacitySensors="capacitySensors"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-capacity-sensors="getAllCapacitySensors"
             >

            </capacitySensor-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CapacitySensorTable from "@/components/CapacitySensorTable";
import CapacitySensorService from "../services/CapacitySensorService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CapacitySensorTable,
  },
  data() {
    return {
      capacitySensors: [],
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
    async getAllCapacitySensors(sortBy='capacitySensorId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CapacitySensorService.getAllCapacitySensors(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.capacitySensors.length) {
					this.capacitySensors = response.data.capacitySensors;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching capacitySensors:", error);
        }
        
      } catch (error) {
        console.error("Error fetching capacitySensor details:", error);
      }
    },
  },
  mounted() {
    this.getAllCapacitySensors();
  },
  created() {
    this.$root.$on('searchQueryForCapacitySensorsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCapacitySensors();
    })
  }
};
</script>
<style></style>
