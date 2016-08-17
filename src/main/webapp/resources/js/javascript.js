

function reactToChangedRadio() {
    alert("I'm in");
}


function coloringAnswer() {

    var value = document.getElementById('myRadio').value;
    var color;
    switch(value)
    {
        case 'Red':
            color = "red";
        break;
        case 'Green':
            color = "green";
        break;
        case 'Blue':
            color = "blue";
        break;
    }
    document.getElementById('red').body.style.backgroundColor = color;
}
