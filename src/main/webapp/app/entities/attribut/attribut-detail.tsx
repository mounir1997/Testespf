import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attribut.reducer';
import { IAttribut } from 'app/shared/model/attribut.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttributDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class AttributDetail extends React.Component<IAttributDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { attributEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.attribut.detail.title">Attribut</Translate> [<b>{attributEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="rctSampleApplicationApp.attribut.code">Code</Translate>
              </span>
            </dt>
            <dd>{attributEntity.code}</dd>
            <dt>
              <span id="valeur">
                <Translate contentKey="rctSampleApplicationApp.attribut.valeur">Valeur</Translate>
              </span>
            </dt>
            <dd>{attributEntity.valeur}</dd>
          </dl>
          <Button tag={Link} to="/entity/attribut" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/attribut/${attributEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ attribut }: IRootState) => ({
  attributEntity: attribut.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttributDetail);
