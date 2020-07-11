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

/* Function to return data */
async function getDataUsingAsyncAwait() {
    const response = await fetch('/data');
    const data = await response.text();
    document.getElementById('data-container').innerText = data;
}

function getMessagesJSON(){
    fetch('/data').then(response => response.json()).then((post)=>{
        const messagListElement = document.getElementById('messages',);
        console.log(post);
        messagListElement.innerHTML = '';
        post.forEach((post) =>{
            messagListElement.appendChild(createListElement(post.comments));            
        });
    }); 
}

function createListElement(text){
    const liElement = document.createElement('li');
    liElement.innerText = text;
    return liElement;
}


