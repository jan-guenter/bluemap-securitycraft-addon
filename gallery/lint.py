#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Lint the generated SecurityCraft gallery without starting Minecraft."""

from __future__ import annotations

import json
from pathlib import Path
import re
import sys

sys.dont_write_bytecode = True
import generate


ROOT = Path(__file__).resolve().parent


def fail(message: str) -> None:
    raise ValueError(message)


def main() -> int:
    expected = generate.generated_files()
    for relative, payload in expected.items():
        path = ROOT / relative
        if not path.is_file() or path.read_bytes() != payload:
            fail(f"generated file differs: {relative}")

    json.loads((ROOT / "datapack/pack.mcmeta").read_text(encoding="utf-8"))
    json.loads(
        (ROOT / "datapack/data/minecraft/tags/function/load.json").read_text(
            encoding="utf-8"
        )
    )

    build = (ROOT / f"datapack/data/{generate.NAMESPACE}/function/build.mcfunction").read_text()
    verify = (ROOT / f"datapack/data/{generate.NAMESPACE}/function/verify.mcfunction").read_text()
    if len(re.findall(r"^setblock ", build, re.MULTILINE)) != 11:
        fail("build must contain exactly 11 placements")
    if len(re.findall(r"^data merge block ", build, re.MULTILINE)) != 4:
        fail("build must contain exactly four disguise payload merges")
    if len(re.findall(r"^scoreboard players add #checked ", verify, re.MULTILINE)) != 16:
        fail("verify must contain exactly 16 retained-state checks")
    if "scoreboard players add #builds sc_gallery 1" not in build:
        fail("one-build counter is missing")

    for _label, x, y, z, _block, _nbt in generate.PLACEMENTS:
        envelope = generate.ENVELOPE
        if not (
            envelope["min_x"] <= x <= envelope["max_x"]
            and envelope["min_y"] <= y <= envelope["max_y"]
            and envelope["min_z"] <= z <= envelope["max_z"]
        ):
            fail(f"placement escaped safe envelope: {(x, y, z)}")

    if 'Properties:{axis:"x"}' not in build:
        fail("oak-log saved-state properties must be strings")
    if 'waterlogged:"false"' not in build:
        fail("oak-stairs saved-state properties must be strings")
    if "analy" in build.lower() or "matrix" in build.lower():
        fail("gallery must remain a direct five-anchor fixture")

    print("SecurityCraft gallery lint passed: 5 anchors, 11 placements, 16 checks/phase")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except ValueError as error:
        print(f"lint failed: {error}", file=sys.stderr)
        raise SystemExit(1)
