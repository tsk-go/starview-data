set -e
cd "$(dirname "$0")"
for spec in "car_sedan.glb 32,48,64" "car_suv.glb 32,48,64" "car_hatch.glb 32,48,64" "car_coupe.glb 32,48,64" \
            "truck.glb 18,26,36 &flip=1" "bus.glb 18,26,36 &flip=1" "motorcycle.glb 40,56,80" "bicycle.glb 40,56,80" "person.glb 40,56,80" \
            "traffic_light.glb 24,36,48" "stop_sign.glb 24,36,48"; do
  set -- $spec
  timeout 600 node run.js "sprites.html?m=$1&pitch=8,16,28&ppm=$2${3:-}" "raw_${1%.glb}.json" 2>&1 | grep -v "GPU stall" || true
done
timeout 600 node run.js "sprites.html?m=ego_sienna.glb&ego=1&D=17.24&cy=0.80&h=2000&w=3200&elev=12,18,24,30,36,42,48" raw_ego.json 2>&1 | grep -v "GPU stall" || true
ls -la raw_*.json
