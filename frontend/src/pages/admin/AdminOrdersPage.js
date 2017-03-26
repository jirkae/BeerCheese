import React from 'react';
import { Container, Button, Table, Jumbotron } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const mockOrders = [
  {
    id: 1,
    user: '/api/users/2',
    status: 'Preparing',
    paymentType: 'Credit card',
    shipping: '/api/shippings/1',
    shippingAddress: '/api/addresses/1',
    billingAddress: '/api/addresses/2',
    discount: 11,
    price: 11,
    links: {
      self: '/api/orders/1',
      ordersPackages: '/api/packages?order=1'
    }
  },
  {
    id: 2,
    user: '/api/users/2',
    status: 'Preparing',
    paymentType: 'Credit card',
    shipping: '/api/shippings/1',
    shippingAddress: '/api/addresses/1',
    billingAddress: '/api/addresses/2',
    discount: 11,
    price: 11,
    links: {
      self: '/api/orders/1',
      ordersPackages: '/api/packages?order=1'
    }
  }
];

const getTableContent = openModal => {
  return mockOrders.map(order => {
    return (
      <tr key={order.id}>
        <td>{order.id}</td>
        <td>{order.user}</td>
        <td>{order.user}</td>
        <td>{order.status}</td>
        <td>
          <Button
            onClick={() => openModal({ name: 'editOrderAdmin', data: order })}
          >
            <i className="fa fa-pencil" />
          </Button>
        </td>
      </tr>
    );
  });
};

const AdminOrdersPage = props => (
  <div>
    <Jumbotron>
      <h1 className="display-4">{localizedTexts.NavBar.orders}</h1>
    </Jumbotron>
    <Container>
      <Table striped>
        <thead>
          <tr>
            <th>{localizedTexts.AdminOrdersPage.id}</th>
            <th>{localizedTexts.AdminOrdersPage.customer}</th>
            <th>{localizedTexts.AdminOrdersPage.email}</th>
            <th>{localizedTexts.AdminOrdersPage.state}</th>
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
})(AdminOrdersPage);
