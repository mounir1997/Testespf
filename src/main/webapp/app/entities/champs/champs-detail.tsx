import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './champs.reducer';
import { IChamps } from 'app/shared/model/champs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChampsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ChampsDetail extends React.Component<IChampsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { champsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.champs.detail.title">Champs</Translate> [<b>{champsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="rctSampleApplicationApp.champs.code">Code</Translate>
              </span>
            </dt>
            <dd>{champsEntity.code}</dd>
            <dt>
              <span id="position">
                <Translate contentKey="rctSampleApplicationApp.champs.position">Position</Translate>
              </span>
            </dt>
            <dd>{champsEntity.position}</dd>
            <dt>
              <span id="longueur">
                <Translate contentKey="rctSampleApplicationApp.champs.longueur">Longueur</Translate>
              </span>
            </dt>
            <dd>{champsEntity.longueur}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="rctSampleApplicationApp.champs.type">Type</Translate>
              </span>
            </dt>
            <dd>{champsEntity.type}</dd>
          </dl>
          <Button tag={Link} to="/entity/champs" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/champs/${champsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ champs }: IRootState) => ({
  champsEntity: champs.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ChampsDetail);
