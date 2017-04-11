import React, { Component } from 'react';
import { Card, CardBlock, CardTitle, CardSubtitle, CardLink} from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const mockProductDetails =  {
    "product": {
      "id": 1,
      "category": "/api/categories/1",
      "name": "Beer",
      "price": 100.0,
      "quantity": 2,
      "priceAfterDiscount": 90.0,
      "active": true,
      "supplier": "/api/suppliers/1",
      "image": "/images/1.jpeg",
      "description": "test description",
      "links": {
        "self": "/api/products/1"
      }
    }
};

class Product extends Component {
  state = {
    product: null
  };

  componentWillMount() {
    //TODO call the rest to get details for given id
    this.setState({
      ...mockProductDetails
    });
  }

  render() {
    if(this.state.product){
      const { product } = this.state;
      return(
        <Card onClick={() => {this.props.openModal({name:'productDetails', data: product})}} style={{margin: '0 25px 25px 0'}}>
          <CardBlock>
            <CardTitle>{product.name}</CardTitle>
          </CardBlock>
          <img width={this.props.size} src="https://placeholdit.imgix.net/~text?txtsize=33&txt=318%C3%97180&w=318&h=180" alt="product" />
          <CardBlock>
            <CardSubtitle>{product.priceAfterDiscount}</CardSubtitle>
            <CardLink href="#">{localizedTexts.Product.btnAddToChart}</CardLink>
          </CardBlock>
        </Card>
      )
    }
  }
}

export default connect(
  null,
  {
    openModal
  }
)(Product)
