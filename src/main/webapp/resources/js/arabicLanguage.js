function checkRtl( character ) {
    var RTL = ['?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?','?'];
    return RTL.indexOf( character ) > -1;
};

var divs = document.getElementsByTagName( 'div' );

for ( var index = 0; index < divs.length; index++ ) {
    if( checkRtl( divs[index].textContent[0] ) ) {
        divs[index].className = 'rtl';
    } else {
        divs[index].className = 'ltr';
    };
};