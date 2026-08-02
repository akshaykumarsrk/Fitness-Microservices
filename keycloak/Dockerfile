# Keycloak, packaged for Render deployment with the fitness-app realm
# auto-imported on every startup (Render's free tier doesn't persist disk,
# so this makes the realm reappear automatically after every restart).

FROM quay.io/keycloak/keycloak:26.4

COPY realm-export.json /opt/keycloak/data/import/realm-export.json

# Render assigns a dynamic port via $PORT — Keycloak must bind to it.
# start-dev is used (not `start`) so we don't need to hand-configure a
# separate production DB just to get Keycloak running for a portfolio demo.
ENTRYPOINT ["sh", "-c", "/opt/keycloak/bin/kc.sh start-dev --import-realm --http-port=${PORT:-8080}"]
