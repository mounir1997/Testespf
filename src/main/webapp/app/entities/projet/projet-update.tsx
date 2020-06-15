import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { getEntity, updateEntity, createEntity, reset } from './projet.reducer';
import { IProjet } from 'app/shared/model/projet.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IProjetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IProjetUpdateState {
  isNew: boolean;
  utilisateurId: number;
}

export class ProjetUpdate extends React.Component<IProjetUpdateProps, IProjetUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      utilisateurId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUtilisateurs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { projetEntity } = this.props;
      const entity = {
        ...projetEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/projet');
  };

  utilisateurUpdate = element => {
    const login = element.target.value.toString();
    if (login === '') {
      this.setState({
        utilisateurId: -1
      });
    } else {
      for (const i in this.props.utilisateurs) {
        if (login === this.props.utilisateurs[i].login.toString()) {
          this.setState({
            utilisateurId: this.props.utilisateurs[i].id
          });
        }
      }
    }
  };

  render() {
    const isInvalid = false;
    const { projetEntity, utilisateurs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="rctSampleApplicationApp.projet.home.createOrEditLabel">
              <Translate contentKey="rctSampleApplicationApp.projet.home.createOrEditLabel">Create or edit a Projet</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projetEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="projet-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomLabel" for="nom">
                    <Translate contentKey="rctSampleApplicationApp.projet.nom">Nom</Translate>
                  </Label>
                  <AvField
                    id="projet-nom"
                    type="text"
                    name="nom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="deviseLabel" for="devise">
                    <Translate contentKey="rctSampleApplicationApp.projet.devise">Devise</Translate>
                  </Label>
                  <AvField
                    id="projet-devise"
                    type="text"
                    name="devise"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="periodiciteLabel" for="periodicite">
                    <Translate contentKey="rctSampleApplicationApp.projet.periodicite">Periodicite</Translate>
                  </Label>
                  <AvField
                    id="projet-periodicite"
                    type="text"
                    name="periodicite"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="rctSampleApplicationApp.projet.date">Date</Translate>
                  </Label>
                  <AvField
                    id="projet-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="utilisateur.login">
                    <Translate contentKey="rctSampleApplicationApp.projet.utilisateur">Utilisateur</Translate>
                  </Label>
                  <AvInput
                    id="projet-utilisateur"
                    type="select"
                    className="form-control"
                    name="utilisateur.login"
                    onChange={this.utilisateurUpdate}
                  >
                    <option value="" key="0" />
                    {utilisateurs
                      ? utilisateurs.map(otherEntity => (
                          <option value={otherEntity.login} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                  <AvInput id="projet-utilisateur" type="hidden" name="utilisateur.id" value={this.state.utilisateurId} />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/projet" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={isInvalid || updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  utilisateurs: storeState.utilisateur.entities,
  projetEntity: storeState.projet.entity,
  loading: storeState.projet.loading,
  updating: storeState.projet.updating
});

const mapDispatchToProps = {
  getUtilisateurs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjetUpdate);
