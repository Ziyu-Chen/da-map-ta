import React from "react";
import { Map, InfoWindow, Marker, GoogleApiWrapper } from "google-maps-react";
import { connect } from "react-redux";
import "./MapContainer.css";

const style = {
  width: "100%",
  height: "500px",
  position: "relative"
};

export function MapContainer(props) {
  return (
    <div className="map-container">
      <Map google={props.google} zoom={5} style={style}>
        {props.locations.length > 0 ? (
          props.locations.map(location => {
            return <Marker position={location.position} />;
          })
        ) : (
          <div></div>
        )}
      </Map>
    </div>
  );
}

const mapStateToProps = state => ({
  locations: state.locations
});

export default connect(mapStateToProps)(
  GoogleApiWrapper({
    apiKey: "AIzaSyDJ3sPnmTBMN1DZGJBX9gxuNg-O9mgHOAo"
  })(MapContainer)
);
