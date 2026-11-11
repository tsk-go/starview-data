#!/usr/bin/env bash
# Hybrid StarView APK: 2.84.1's own compiled code for every class we didn't change on purpose, our build for the rest
# (see merge.py for why). Resources, manifest, native libraries and assets stay exactly as Gradle built them; only
# the classes*.dex files are replaced. The result still has to be signed (apksigner).
#
#   build.sh GRADLE_APK BASE_APK APKTOOL_JAR OUT_APK
#     GRADLE_APK  app/app/build/outputs/apk/debug/app-debug.apk
#     BASE_APK    StarView 2.84.1 (release "base-2.84.1" of this repo)
#     APKTOOL_JAR apktool 2.10.0 (it brings smali / baksmali)
#     OUT_APK     the hybrid APK, zip-aligned, unsigned
set -euo pipefail
here=$(cd "$(dirname "$0")" && pwd)
gradle_apk=$(realpath "$1"); base_apk=$(realpath "$2"); apktool=$(realpath "$3"); out=$4
zipalign=${ZIPALIGN:-$(ls -d "${ANDROID_HOME:-/opt/android-sdk}"/build-tools/*/ | sort -V | tail -1)zipalign}
w=$(mktemp -d)
trap 'rm -rf "$w"' EXIT

java -jar "$apktool" d -r -f -o "$w/o" "$base_apk" >/dev/null
java -jar "$apktool" d -r -f -o "$w/r" "$gradle_apk" >/dev/null
python3 "$here/merge.py" "$w/o" "$w/r" "$w/m"
python3 "$here/check.py" "$w/m" | tail -40        # prints every problem; exit code below
python3 "$here/check.py" "$w/m" >/dev/null || { echo "hybrid: link check failed" >&2; exit 1; }

cp -r "$w/r" "$w/b"
rm -rf "$w"/b/smali*
cp -r "$w"/m/smali* "$w/b/"
java -jar "$apktool" b "$w/b" -o "$w/hyb.apk" >/dev/null

mkdir "$w/dex"
(cd "$w/dex" && unzip -q "$w/hyb.apk" 'classes*.dex')
cp "$gradle_apk" "$w/out.apk"
zip -q -d "$w/out.apk" 'classes*.dex' 'META-INF/*.SF' 'META-INF/*.RSA' 'META-INF/*.EC' 'META-INF/MANIFEST.MF' 2>/dev/null || true
(cd "$w/dex" && zip -q "$w/out.apk" classes*.dex)
"$zipalign" -p -f 4 "$w/out.apk" "$out"
echo "hybrid: $(ls "$w"/dex | tr '\n' ' ')-> $out"
