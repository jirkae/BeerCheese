import React, { Component } from 'react';
import { Row, Container } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';
// import localizedTexts from '../../text_localization/LocalizedStrings';


export default class PackageOverviewSummaryPage extends Component {
  state = {
    beerCategoryExpanded: false,
    supplCategoryExpanded: false
  };

 render(){
    return (
      <Container>
        <PackageOverviewNav stage={4}/>
        <Row>
          PackageOverviewSummaryPage
        </Row>
      </Container>
    );
  }
}
