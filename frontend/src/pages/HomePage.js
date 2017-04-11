import React, { Component } from 'react';
import {
  Row,
  Col,
  Button,
  Nav,
  NavItem,
  NavLink,
  NavbarBrand
} from 'reactstrap';
import ProductList from '../components/product/ProductList';
import WelcomeWarningPopUp from '../components/popup/WelcomeWarningPopUp';
import localizedTexts from '../text_localization/LocalizedStrings';
import { Link } from 'react-router';

const mockBeerCategories = ['black', 'lager'];
const mockSupplementsCategories = ['glass', 'beerMat'];

export default class HomePage extends Component {
  state = {
    beerCategoryExpanded: false,
    supplCategoryExpanded: false
  };

  renderNavBeerItems = () => {
    if (this.state.beerCategoryExpanded) {
      return mockBeerCategories.map(value => (
        <NavItem>
          <NavLink href="#">{localizedTexts.categories[value]}</NavLink>
        </NavItem>
      ));
    }
    return null;
  };

  renderNavSupplementsItems = () => {
    if (this.state.supplCategoryExpanded) {
      return mockSupplementsCategories.map(value => (
        <NavItem>
          <NavLink href="#">{localizedTexts.categories[value]}</NavLink>
        </NavItem>
      ));
    }
    return null;
  };

  render() {
    return (
      <Row>
        <WelcomeWarningPopUp />
        <Col xl="2" lg="2" md="4" sm="12" xs="12">
          <Nav pills vertical>
            <Button
              outline
              color="primary"
              tag={Link}
              to="/create_package_beer"
            >
              {localizedTexts.HomePage.createPackage}
            </Button>
            <NavbarBrand>{localizedTexts.HomePage.categories}</NavbarBrand>
            <NavItem>
              <NavLink
                onClick={() =>
                  this.setState({
                    beerCategoryExpanded: !this.state.beerCategoryExpanded
                  })}
                href="#"
              >
                {localizedTexts.HomePage.beer}
              </NavLink>
            </NavItem>
            {this.renderNavBeerItems()}
            <NavItem>
              <NavLink
                onClick={() =>
                  this.setState({
                    supplCategoryExpanded: !this.state.supplCategoryExpanded
                  })}
                href="#"
              >
                {localizedTexts.HomePage.supplements}
              </NavLink>
            </NavItem>
            {this.renderNavSupplementsItems()}
          </Nav>
        </Col>
        <Col xl="10" lg="10" md="8" sm="12" xs="12">

          <ProductList />
          <Button>{localizedTexts.HomePage.previous}</Button>
          <Button>{localizedTexts.HomePage.next}</Button>

        </Col>
      </Row>
    );
  }
}
