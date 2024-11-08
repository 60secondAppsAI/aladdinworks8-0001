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
            <networkSwitch-table
            v-if="networkSwitchs && networkSwitchs.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:networkSwitchs="networkSwitchs"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-network-switchs="getAllNetworkSwitchs"
             >

            </networkSwitch-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import NetworkSwitchTable from "@/components/NetworkSwitchTable";
import NetworkSwitchService from "../services/NetworkSwitchService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    NetworkSwitchTable,
  },
  data() {
    return {
      networkSwitchs: [],
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
    async getAllNetworkSwitchs(sortBy='networkSwitchId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await NetworkSwitchService.getAllNetworkSwitchs(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.networkSwitchs.length) {
					this.networkSwitchs = response.data.networkSwitchs;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching networkSwitchs:", error);
        }
        
      } catch (error) {
        console.error("Error fetching networkSwitch details:", error);
      }
    },
  },
  mounted() {
    this.getAllNetworkSwitchs();
  },
  created() {
    this.$root.$on('searchQueryForNetworkSwitchsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllNetworkSwitchs();
    })
  }
};
</script>
<style></style>
