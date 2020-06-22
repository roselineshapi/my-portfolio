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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
/* Function to return data */
async function getDataUsingAsyncAwait() {
  const response = await fetch('/data');
  const data = await response.text();
  document.getElementById('data-container').innerText = data;
}

function getMessagesJSON(){
    fetch('/data').then(response => response.json()).then((message)=>{
        const messagListElement = document.getElementById('messages');
        console.log(message);
        message.forEach(line =>{
            let listItem = document.createElement("li");  
            messagListElement.appendChild(listItem);            
            listItem.innerHTML = line;
        });
    }); 
}


function createListElement(text){
    const liElement = document.createListElement('li');
    liElement.innerText = text;
    return liElement;
}














// function getSubtractionGame() {
//   fetch('/subtraction-game').then(response => response.json()).then((game) => {
//     const totalEl = document.getElementById('total');
//     if (game.gameOver) {
//       // The current game is over, show the total for the next game.
//       totalEl.innerText = 'Total: 21';
//     } else {
//       totalEl.innerText = 'Total: ' + game.currentTotal;
//     }

//     // Build the list of history entries.
//     const historyEl = document.getElementById('history');
//     game.history.forEach((line) => {
//       historyEl.appendChild(createListElement(line));
//     });
//   });
// }
