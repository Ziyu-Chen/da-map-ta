import React, { Component } from "react";
import { Map, InfoWindow, Marker, GoogleApiWrapper } from "google-maps-react";
import { connect } from "react-redux";
import "./MapContainer.css";

const style = {
  width: "100%",
  height: "500px",
  position: "relative"
};

export class MapContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showingInfoWindow: false,
      activeMarker: null,
      selectedLocation: null
    };
  }
  onMarkerClick = (props, marker, e, selectedLocation) => {
    this.setState({
      selectedLocation: selectedLocation,
      activeMarker: marker,
      showingInfoWindow: true
    });
  };
  onMapClick = () => {
    if (this.showingInfoWindow) {
      this.setState({
        showingInfoWindow: false,
        activeMarker: null
      });
    }
  };
  render() {
    return (
      <div className="map-container">
        {this.props.locations.length > 0 ? (
          <Map
            google={this.props.google}
            zoom={5}
            style={style}
            onClick={this.onMapClick}
          >
            {this.props.locations.length > 0 ? (
              this.props.locations.map((location, index) => {
                return (
                  <Marker
                    position={location.position}
                    onClick={(props, marker, e) =>
                      this.onMarkerClick(props, marker, e, location)
                    }
                    key={index}
                  />
                );
              })
            ) : (
              <div></div>
            )}
            <InfoWindow
              marker={this.state.activeMarker}
              visible={this.state.showingInfoWindow}
            >
              <div>
                {this.state.selectedLocation ? (
                  Object.keys(this.state.selectedLocation).map(key => {
                    if (key === "position") {
                      return (
                        <div>
                          {"position: " +
                            this.state.selectedLocation.position.lat +
                            ", " +
                            this.state.selectedLocation.position.lng}
                        </div>
                      );
                    } else {
                      return (
                        <div>
                          {key + ": " + this.state.selectedLocation[key]}
                        </div>
                      );
                    }
                  })
                ) : (
                  <div></div>
                )}
              </div>
            </InfoWindow>
          </Map>
        ) : (
          <div></div>
        )}
      </div>
    );
  }
}

const mapStateToProps = state => ({
  locations: state.locations
});

export default connect(mapStateToProps)(
  GoogleApiWrapper({
    apiKey: "AIzaSyDJ3sPnmTBMN1DZGJBX9gxuNg-O9mgHOAo"
  })(MapContainer)
);
