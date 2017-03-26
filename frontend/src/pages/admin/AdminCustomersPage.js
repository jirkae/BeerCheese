import React from 'react';
import { Container, Button, Table, Jumbotron } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const mockCustomers = [
  {
    user: {
      id: 1,
      login: 'dummy',
      firstName: 'Jan',
      lastName: 'Jiri',
      email: 'dummy@dummy.io',
      phoneNumber: '+420777888777',
      birthday: '31/12/2011',
      links: {
        self: '/api/users/{id}',
        orders: '/api/orders?user={id}',
        addresses: '/api/addresses?user={id}',
        roles: '/api/roles?user={id}'
      }
    }
  },
  {
    user: {
      id: 2,
      login: 'dummy',
      firstName: 'Jan',
      lastName: 'Jiri',
      email: 'dummy@dummy.io',
      phoneNumber: '+420777888777',
      birthday: '31/12/2011',
      links: {
        self: '/api/users/{id}',
        orders: '/api/orders?user={id}',
        addresses: '/api/addresses?user={id}',
        roles: '/api/roles?user={id}'
      }
    }
  }
];

const getTableContent = openModal => {
  return mockCustomers.map(user => {
    return (
      <tr key={user.id}>
        <td>{user.user.id}</td>
        <td>{user.user.firstName}</td>
        <td>{user.user.lastName}</td>
        <td>{user.user.email}</td>
        <td>
          <Button
            onClick={() =>
              openModal({ name: 'editCustomerAdmin', data: user.user })}
          >
            <i className="fa fa-pencil" />
          </Button>
        </td>
      </tr>
    );
  });
};

const AdminCustomersPage = props => (
  <div>
    <Jumbotron>
      <h1 className="display-4">{localizedTexts.NavBar.customers}</h1>
    </Jumbotron>
    <Container>
      <Table striped>
        <thead>
          <tr>
            <th>{localizedTexts.AdminProductsPage.id}</th>
            <th>{localizedTexts.AdminProductsPage.name}</th>
            <th>{localizedTexts.AdminProductsPage.surname}</th>
            <th>{localizedTexts.AdminProductsPage.email}</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {getTableContent(props.openModal)}
        </tbody>
      </Table>
    </Container>
  </div>
);

export default connect(null, {
  openModal
})(AdminCustomersPage);
