import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './valeur-champ.reducer';
import { IValeurChamp } from 'app/shared/model/valeur-champ.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IValeurChampDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ValeurChampDetail extends React.Component<IValeurChampDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { valeurChampEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.valeurChamp.detail.title">ValeurChamp</Translate> [<b>{valeurChampEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="valeur">
                <Translate contentKey="rctSampleApplicationApp.valeurChamp.valeur">Valeur</Translate>
              </span>
            </dt>
            <dd>{valeurChampEntity.valeur}</dd>
          </dl>
          <Button tag={Link} to="/entity/valeur-champ" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/valeur-champ/${valeurChampEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ valeurChamp }: IRootState) => ({
  valeurChampEntity: valeurChamp.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ValeurChampDetail);
