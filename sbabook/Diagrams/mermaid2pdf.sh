#!/bin/sh
# Render a Mermaid class diagram to a cropped PDF figure, with Smalltalk-style
# selectors: Mermaid requires "()" to put a member in the methods compartment,
# so the "()" are stripped from the rendered SVG before printing it to PDF.
#
# Usage (from the sbabook folder):
#   Diagrams/mermaid2pdf.sh Diagrams/InterpolationClassDiagram.mmd Figures/InterpolationClassDiagram.pdf
#
# Needs: node/npx (for @mermaid-js/mermaid-cli), Google Chrome, pdfcrop (TeX Live).
set -e
in=$1
out=$2
here=$(cd "$(dirname "$0")" && pwd)
chrome=${CHROME:-"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"}
tmp=$(mktemp -d)
trap 'rm -rf "$tmp"' EXIT

npx -y -p @mermaid-js/mermaid-cli mmdc -q -i "$in" -o "$tmp/diagram.svg" -c "$here/mermaid-config.json"
sed 's/()<\/p>/<\/p>/g' "$tmp/diagram.svg" > "$tmp/diagram-st.svg"
cat > "$tmp/page.html" <<'EOF'
<!doctype html><html><head><style>@page{size:A3;margin:0}html,body{margin:0;background:#fff}img{display:block}</style></head>
<body><img src="diagram-st.svg"></body></html>
EOF
"$chrome" --headless --disable-gpu --no-pdf-header-footer --print-to-pdf="$tmp/page.pdf" "file://$tmp/page.html" 2>/dev/null
pdfcrop --margins 2 "$tmp/page.pdf" "$out" >/dev/null
echo "Wrote $out"
