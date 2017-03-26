import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
// import ProductList from '../../components/product/ProductList';
import PackageCreationNavigation
  from '../../components/navigation/PackageCreationNav';
// import localizedTexts from '../../text_localization/LocalizedStrings';

export default class CreatePackageSummaryPage extends Component {
  state = {
    beerCategoryExpanded: false,
    supplCategoryExpanded: false
  };

  render() {
    return (
      <Container>
        <PackageCreationNavigation stage={5} />
        <Row>
          Summary
        </Row>
      </Container>
    );
  }
}
