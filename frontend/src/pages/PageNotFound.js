import React from 'react';
import { Link } from 'react-router';

const PageNotFound = () => {
  // document.body.style.backgroundImage = "url(https://static.pexels.com/photos/132037/pexels-photo-132037.jpeg)";
  return (
    <div className="row pad-t-5">
      <div className="container white">
        <h1 className="v-o-4">
          <img
            src="http://icons.iconarchive.com/icons/double-j-design/ravenna-3d/256/Error-icon.png"
            alt="error"
            width="5%"
          />
          {' '}
          Error 404 Page not Found
        </h1>
        <h1>
          Maybe you used bad link. You can back to <Link to="/">Homepage</Link>
        </h1>
      </div>
    </div>
  );
};

export default PageNotFound;
