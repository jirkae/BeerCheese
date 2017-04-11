import React from 'react';
import { Link } from 'react-router';
import { Row, Col } from 'reactstrap';
import localizedStrings from '../../text_localization/LocalizedStrings';
import { css } from 'glamor';
import CartContent from '../CartContent';

export default class NavPanel extends React.Component {
  state = {
    price: 9000,
    amount: 3,
    showCartContent: false
  };

  render() {
    return (
      <div>
          <Row>
            <Col xs="7">
              <ul className={`${this.cssCartControl}`}>
                <li>
                  {localizedStrings.NavPanel.amount}: {this.state.amount} ks
                </li>
                <li>
                  {localizedStrings.NavPanel.price}: {this.state.price} Kč
                </li>
              </ul>
            </Col>
            <Col xs="5">
              <Link
                to="#"
                className={`${this.cssExpandLink}`}
                onClick={() =>
                  this.setState({
                    showCartContent: !this.state.showCartContent
                  })}
              >
                <i className="fa fa-expand fa-3" />
              </Link>
            </Col>
          </Row>
        {this.state.showCartContent ? <CartContent /> : ''}
      </div>
    );
  }

  cssNavPanel = css({
    margin: 0,
    padding: '5px',
    height: '70px'
  });

  cssCartControl = css({
    listStyleType: 'none',
    minWidth: '140px',
    paddingLeft: '20px',
    paddingTop: '5px'
  });

  cssExpandLink = css({
    textDecoration: 'none',
    color: 'black'
  });
}
