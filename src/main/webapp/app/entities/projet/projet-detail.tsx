import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './projet.reducer';
import { IProjet } from 'app/shared/model/projet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class ProjetDetail extends React.Component<IProjetDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { projetEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.projet.detail.title">Projet</Translate> [<b>{projetEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">
                <Translate contentKey="rctSampleApplicationApp.projet.nom">Nom</Translate>
              </span>
            </dt>
            <dd>{projetEntity.nom}</dd>
            <dt>
              <span id="devise">
                <Translate contentKey="rctSampleApplicationApp.projet.devise">Devise</Translate>
              </span>
            </dt>
            <dd>{projetEntity.devise}</dd>
            <dt>
              <span id="periodicite">
                <Translate contentKey="rctSampleApplicationApp.projet.periodicite">Periodicite</Translate>
              </span>
            </dt>
            <dd>{projetEntity.periodicite}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="rctSampleApplicationApp.projet.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={projetEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="rctSampleApplicationApp.projet.utilisateur">Utilisateur</Translate>
            </dt>
            <dd>{projetEntity.utilisateur ? projetEntity.utilisateur.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/projet" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/projet/${projetEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ projet }: IRootState) => ({
  projetEntity: projet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjetDetail);
