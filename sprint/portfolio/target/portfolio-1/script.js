// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
  
// Allows comments to be displayed everytime the page loads
window.onload = function() {
  addResults();
  
};

// Adds map to page
/*var map;
function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: -19.0154, lng: 29.1549},
    zoom: 0
  });
*/
   
async function addResults(){

   // Sends a request to /my-data-url 
   fetch('/data')
   // Parses the response as JSON  
.then(response => response.json()) 
  // References the fields in the object 
.then((resultsFinal) => { 
  console.log(resultsFinal);

  const resultsContainer = document.getElementById('results');
  
  resultsContainer.innerHTML = resultsFinal.join("<br>");
});
}
