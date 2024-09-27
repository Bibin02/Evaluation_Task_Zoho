export async function loadJSON(endpoint) {
    let response = await fetch(endpoint);
    if (response.status === 200) {
        return response.json()
    }
    else{
        console.log(response.statusText);
        return null;
    }
}