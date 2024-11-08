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
            <staticTransferSwitch-table
            v-if="staticTransferSwitchs && staticTransferSwitchs.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:staticTransferSwitchs="staticTransferSwitchs"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-static-transfer-switchs="getAllStaticTransferSwitchs"
             >

            </staticTransferSwitch-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import StaticTransferSwitchTable from "@/components/StaticTransferSwitchTable";
import StaticTransferSwitchService from "../services/StaticTransferSwitchService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    StaticTransferSwitchTable,
  },
  data() {
    return {
      staticTransferSwitchs: [],
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
    async getAllStaticTransferSwitchs(sortBy='staticTransferSwitchId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await StaticTransferSwitchService.getAllStaticTransferSwitchs(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.staticTransferSwitchs.length) {
					this.staticTransferSwitchs = response.data.staticTransferSwitchs;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching staticTransferSwitchs:", error);
        }
        
      } catch (error) {
        console.error("Error fetching staticTransferSwitch details:", error);
      }
    },
  },
  mounted() {
    this.getAllStaticTransferSwitchs();
  },
  created() {
    this.$root.$on('searchQueryForStaticTransferSwitchsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllStaticTransferSwitchs();
    })
  }
};
</script>
<style></style>
