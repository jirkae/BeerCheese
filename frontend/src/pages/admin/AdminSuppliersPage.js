import React from 'react';
import { Container, Button, Table, Jumbotron } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const mockSuppliers = [
  {
    id: 1,
    category: '/api/categories/1',
    name: 'Krušovice',
    price: 100.0,
    quantity: 2,
    priceAfterDiscount: 90.0,
    active: true,
    email: 'email@muj.cz',
    image: '/images/1.jpeg',
    description: 'test description',
    links: {
      self: '/api/products/1'
    }
  },
  {
    id: 2,
    category: '/api/categories/1',
    name: 'Beer',
    price: 100.0,
    quantity: 2,
    priceAfterDiscount: 90.0,
    active: true,
    email: 'email@muj.cz',
    image: '/images/1.jpeg',
    description: 'test description',
    links: {
      self: '/api/products/1'
    }
  }
];

const getTableContent = openModal => {
  return mockSuppliers.map(supplier => {
    return (
      <tr key={supplier.id}>
        <td>{supplier.id}</td>
        <td>{supplier.name}</td>
        <td>{supplier.email}</td>
        <td>
          <Button
            onClick={() =>
              openModal({ name: 'editSupplierAdmin', data: supplier })}
          >
            <i className="fa fa-pencil" />
          </Button>
        </td>
      </tr>
    );
  });
};

const AdminSuppliersPage = props => (
  <div>
    <Jumbotron>
      <h1 className="display-4">{localizedTexts.NavBar.suppliers}</h1>
    </Jumbotron>
    <Container>
      <Table striped>
        <thead>
          <tr>
            <th>{localizedTexts.AdminSuppliersPage.id}</th>
            <th>{localizedTexts.AdminSuppliersPage.name}</th>
            <th>{localizedTexts.AdminSuppliersPage.email}</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {getTableContent(props.openModal)}
        </tbody>
      </Table>
      <Button
        onClick={() =>
          props.openModal({ name: 'newSupplierAdmin', data: null })}
      >
        {localizedTexts.AdminSuppliersPage.btnAddSupplier}
      </Button>
    </Container>
  </div>
);

export default connect(null, {
  openModal
})(AdminSuppliersPage);
