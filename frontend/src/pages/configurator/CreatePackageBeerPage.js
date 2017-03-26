import React, { Component } from 'react';
import {
  Row,
  Col,
  Jumbotron,
  Button,
  Nav,
  NavItem,
  NavLink,
  NavbarBrand,
  Container
} from 'reactstrap';
import ProductList from '../../components/product/ProductList';
import PackageCreationNavigation
  from '../../components/navigation/PackageCreationNav';
import localizedTexts from '../../text_localization/LocalizedStrings';

const mockBeerCategories = ['black', 'lager'];

export default class CreatePackageBeerPage extends Component {
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

  render() {
    return (
      <Container>
        <PackageCreationNavigation stage={1} />
        <Row>
          <Col xl="2" lg="2" md="4" sm="12" xs="12">
            <Nav vertical>
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
            </Nav>
          </Col>
          <Col xl="10" lg="10" md="8" sm="12" xs="12">
            <Jumbotron>
              <ProductList />
              <Button>{localizedTexts.HomePage.previous}</Button>
              <Button>{localizedTexts.HomePage.next}</Button>
            </Jumbotron>
          </Col>
        </Row>
      </Container>
    );
  }
}
