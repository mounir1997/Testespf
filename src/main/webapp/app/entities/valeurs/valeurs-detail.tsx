import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './valeurs.reducer';
import { IValeurs } from 'app/shared/model/valeurs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IValeursDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ValeursDetail extends React.Component<IValeursDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { valeursEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.valeurs.detail.title">Valeurs</Translate> [<b>{valeursEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="valeur">
                <Translate contentKey="rctSampleApplicationApp.valeurs.valeur">Valeur</Translate>
              </span>
            </dt>
            <dd>{valeursEntity.valeur}</dd>
          </dl>
          <Button tag={Link} to="/entity/valeurs" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/valeurs/${valeursEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ valeurs }: IRootState) => ({
  valeursEntity: valeurs.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ValeursDetail);
