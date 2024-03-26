    // First, parse the query string
    var params = {}, queryString = location.hash.substring(1),
        regex = /([^&=]+)=([^&]*)/g, m;
    while (m = regex.exec(queryString)) {
      params[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
    }
    // And send the token over to the server
    var req = new XMLHttpRequest();
    // consider using POST so query isn't logged
    req.open('GET', 'http://localhost:8080/saveToken?' + queryString, true);
    req.onreadystatechange = function (e) {
      if (req.status === 200) {
        alert('Ok');
      }
      else {
        alert('Not ok: ' + req.status);
      }
    };
    req.send(null);