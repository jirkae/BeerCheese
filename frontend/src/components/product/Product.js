import React from 'react';
import { Card, CardBlock, CardTitle, CardSubtitle, CardLink } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';

const Product = ({ product, openModal }) => (
  <Card
    onClick={() => {
      openModal({ name: 'productDetails', data: product });
    }}
  >
    <CardBlock>
      <CardTitle>{product.name}</CardTitle>
    </CardBlock>
    <img
      width="100%"
      src="https://placeholdit.imgix.net/~text?txtsize=33&amp;txt=318%C3%97180&amp;w=318&amp;h=180"
      alt="product"
    />
    <CardBlock>
      <CardSubtitle>{product.priceAfterDiscount}</CardSubtitle>
      <CardLink href="#">{localizedTexts.Product.btnAddToChart}</CardLink>
    </CardBlock>
  </Card>
);

export default connect(null, {
  openModal
})(Product);
