import * as React from 'react';
import { Container } from 'reactstrap';
import NavBar from '../components/NavBar';

const RootPage = ({ children }) => {
  return (
    <Container>
      <NavBar />
        { children }
    </Container>
  );
};

export default RootPage;
