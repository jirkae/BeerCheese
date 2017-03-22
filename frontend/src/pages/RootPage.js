import * as React from 'react';
import NavBar from '../components/NavBar';
import { Container} from 'reactstrap';

const RootPage = ({ children }) => {
  return (
    <Container>
      <NavBar />
        {children}
    </Container>
  );
};

export default RootPage;
