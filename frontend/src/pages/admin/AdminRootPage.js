import React from 'react';
import { Container } from 'reactstrap';
import NavBarAdmin from '../../components/navigation/NavBarAdmin';
import Modals from '../../components/navigation/Modals';

const AdminPage = ({ children }) =>
  (
      <div>
        <Container>
          <NavBarAdmin />
          <Modals />
          { children }
        </Container>
      </div>
    );

export default AdminPage;
