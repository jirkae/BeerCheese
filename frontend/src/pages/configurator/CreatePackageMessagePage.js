import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
import PackageCreationNavigation from '../../components/navigation/PackageCreationNav';
import localizedTexts from '../../text_localization/LocalizedStrings';

export default class CreatePackageMessagePage extends Component {
  state = {
    beerCategoryExpanded: false,
    supplCategoryExpanded: false
  };

 render(){
    return (
      <Container>
        <PackageCreationNavigation stage={4}/>
        <Row>
          Message
        </Row>
      </Container>
    );
  }
}
