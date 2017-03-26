import React from 'react';
import { Container, Button, Table, Jumbotron } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const mockProducts = [
  {
    id: 1,
    category: '/api/categories/1',
    name: 'Beer',
    price: 100.0,
    quantity: 2,
    priceAfterDiscount: 90.0,
    active: true,
    supplier: '/api/suppliers/1',
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
    supplier: '/api/suppliers/1',
    image: '/images/1.jpeg',
    description: 'test description',
    links: {
      self: '/api/products/1'
    }
  }
];

const getTableContent = openModal => {
  return mockProducts.map(product => {
    return (
      <tr key={product.id}>
        <td>{product.id}</td>
        <td>{product.name}</td>
        <td>{product.category}</td>
        <td>{product.price}</td>
        <td>{product.supplier}</td>
        <td>
          <Button
            onClick={() =>
              openModal({ name: 'editProductAdmin', data: product })}
          >
            <i className="fa fa-pencil" />
          </Button>
        </td>
      </tr>
    );
  });
};

const AdminProductsPage = props => (
  <div>
    <Jumbotron>
      <h1 className="display-4">{localizedTexts.NavBar.products}</h1>
    </Jumbotron>
    <Container>
      <Table striped>
        <thead>
          <tr>
            <th>{localizedTexts.AdminProductsPage.id}</th>
            <th>{localizedTexts.AdminProductsPage.name}</th>
            <th>{localizedTexts.AdminProductsPage.category}</th>
            <th>{localizedTexts.AdminProductsPage.price}</th>
            <th>{localizedTexts.AdminProductsPage.supplier}</th>
            <th />
          </tr>
        </thead>
        <tbody>
          {getTableContent(props.openModal)}
        </tbody>
      </Table>
      <Button
        onClick={() => props.openModal({ name: 'newProductAdmin', data: null })}
      >
        {localizedTexts.AdminProductsPage.btnAddProduct}
      </Button>
    </Container>
  </div>
);

export default connect(null, {
  openModal
})(AdminProductsPage);
