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
            <serviceContract-table
            v-if="serviceContracts && serviceContracts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:serviceContracts="serviceContracts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-service-contracts="getAllServiceContracts"
             >

            </serviceContract-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ServiceContractTable from "@/components/ServiceContractTable";
import ServiceContractService from "../services/ServiceContractService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ServiceContractTable,
  },
  data() {
    return {
      serviceContracts: [],
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
    async getAllServiceContracts(sortBy='serviceContractId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ServiceContractService.getAllServiceContracts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.serviceContracts.length) {
					this.serviceContracts = response.data.serviceContracts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching serviceContracts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching serviceContract details:", error);
      }
    },
  },
  mounted() {
    this.getAllServiceContracts();
  },
  created() {
    this.$root.$on('searchQueryForServiceContractsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllServiceContracts();
    })
  }
};
</script>
<style></style>
